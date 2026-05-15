const state = {
    page: 0,
    size: 10,
    country: "",
    sortBy: "totalPoints",
    direction: "desc"
};

const eventLabels = {
    M100: "100 m",
    LONG_JUMP: "Kaugushüpe",
    SHOT_PUT: "Kuulitõuge",
    HIGH_JUMP: "Kõrgushüpe",
    M400: "400 m",
    M110_HURDLES: "110 m tõkked",
    DISCUS_THROW: "Kettaheide",
    POLE_VAULT: "Teivashüpe",
    JAVELIN_THROW: "Odavise",
    M1500: "1500 m"
};

const statusBox = document.querySelector("#status");
const rows = document.querySelector("#athlete-rows");
const athleteSelect = document.querySelector("#result-athlete");
const eventSelect = document.querySelector("#result-event");
const pageLabel = document.querySelector("#page-label");
const prevPage = document.querySelector("#prev-page");
const nextPage = document.querySelector("#next-page");

let currentPage = null;

function setStatus(message, isError = false) {
    statusBox.textContent = message;
    statusBox.classList.toggle("error", isError);
}

async function request(url, options = {}) {
    const response = await fetch(url, {
        headers: {
            "Content-Type": "application/json",
            ...options.headers
        },
        ...options
    });

    if (!response.ok) {
        const error = await response.json().catch(() => ({}));
        throw new Error(error.message || `Päring ebaõnnestus (${response.status})`);
    }

    if (response.status === 204) {
        return null;
    }

    return response.json();
}

function athleteQuery(overrides = {}) {
    const next = { ...state, ...overrides };
    const params = new URLSearchParams({
        page: next.page,
        size: next.size,
        sortBy: next.sortBy,
        direction: next.direction
    });

    if (next.country) {
        params.set("country", next.country);
    }

    return `/athletes?${params.toString()}`;
}

async function loadEvents() {
    const events = await request("/athletes/events");
    eventSelect.innerHTML = events
        .map(event => `<option value="${event}">${eventLabels[event] || event}</option>`)
        .join("");
}

async function loadAthletes() {
    currentPage = await request(athleteQuery());
    renderAthletes(currentPage.content);
    renderPager();
    await refreshAthleteSelect();
}

async function refreshAthleteSelect() {
    const page = await request(athleteQuery({ page: 0, size: 100, sortBy: "name", direction: "asc" }));
    athleteSelect.innerHTML = page.content
        .map(athlete => `<option value="${athlete.id}">${athlete.name} (${athlete.country})</option>`)
        .join("");
}

function renderAthletes(athletes) {
    if (athletes.length === 0) {
        rows.innerHTML = `<tr><td colspan="4" class="muted">Sportlasi ei leitud.</td></tr>`;
        return;
    }

    rows.innerHTML = athletes.map(athlete => `
        <tr>
            <td>${escapeHtml(athlete.name)}</td>
            <td>${escapeHtml(athlete.country)}</td>
            <td class="points">${athlete.totalPoints}</td>
            <td>${renderResults(athlete.results || [])}</td>
        </tr>
    `).join("");
}

function renderResults(results) {
    if (results.length === 0) {
        return `<span class="muted">Tulemusi pole</span>`;
    }

    return `<div class="result-list">${results.map(result => `
        <span class="result-pill">${eventLabels[result.event] || result.event}: ${result.performance} (${result.points} p)</span>
    `).join("")}</div>`;
}

function renderPager() {
    const totalPages = currentPage?.totalPages || 0;
    const humanPage = totalPages === 0 ? 0 : state.page + 1;

    pageLabel.textContent = `${humanPage} / ${totalPages}`;
    prevPage.disabled = state.page === 0;
    nextPage.disabled = totalPages === 0 || state.page + 1 >= totalPages;
}

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

document.querySelector("#athlete-form").addEventListener("submit", async event => {
    event.preventDefault();
    const form = new FormData(event.currentTarget);

    try {
        await request("/athletes", {
            method: "POST",
            body: JSON.stringify({
                name: form.get("name"),
                country: form.get("country")
            })
        });
        event.currentTarget.reset();
        state.page = 0;
        await loadAthletes();
        setStatus("Sportlane lisatud.");
    } catch (error) {
        setStatus(error.message, true);
    }
});

document.querySelector("#result-form").addEventListener("submit", async event => {
    event.preventDefault();
    const form = new FormData(event.currentTarget);
    const athleteId = form.get("athleteId");

    try {
        await request(`/athletes/${athleteId}/results`, {
            method: "POST",
            body: JSON.stringify({
                event: form.get("event"),
                performance: Number(form.get("performance"))
            })
        });
        document.querySelector("#result-performance").value = "";
        await loadAthletes();
        setStatus("Tulemus lisatud.");
    } catch (error) {
        setStatus(error.message, true);
    }
});

document.querySelector("#filter-form").addEventListener("input", async event => {
    const form = new FormData(event.currentTarget);
    state.country = String(form.get("country") || "").trim();
    state.sortBy = form.get("sortBy");
    state.direction = form.get("direction");
    state.size = Number(form.get("size"));
    state.page = 0;

    try {
        await loadAthletes();
        setStatus("");
    } catch (error) {
        setStatus(error.message, true);
    }
});

prevPage.addEventListener("click", async () => {
    state.page = Math.max(0, state.page - 1);
    await loadAthletes();
});

nextPage.addEventListener("click", async () => {
    state.page += 1;
    await loadAthletes();
});

Promise.all([loadEvents(), loadAthletes()])
    .then(() => setStatus(""))
    .catch(error => setStatus(error.message, true));
