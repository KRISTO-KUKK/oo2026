import { useEffect, useState } from "react";
import type { BibleBook } from "../models/BibleBook";

function BiblesPage() {
  const [bibles, setBibles] = useState<BibleBook[]>([]);

  useEffect(() => {
    fetch(import.meta.env.VITE_BACK_URL + "/bibles")
      .then(res => res.json())
      .then(json => setBibles(json))
  }, []);

  return (
    <div>
      <h2>Piibli raamatud välisest API-st</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Keel</th>
            <th>Versioon</th>
          </tr>
        </thead>
        <tbody>
          {bibles.map(bible =>
            <tr key={bible.bible_id}>
              <td>{bible.bible_id}</td>
              <td>{bible.language}</td>
              <td>{bible.version ?? ""}</td>
            </tr>)}
        </tbody>
      </table>
    </div>
  )
}

export default BiblesPage
