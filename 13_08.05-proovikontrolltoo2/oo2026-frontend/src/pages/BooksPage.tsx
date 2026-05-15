import { useEffect, useState } from "react";
import type { Book } from "../models/Book";

type BookPage = {
  content: Book[],
  number: number,
  totalPages: number,
  totalElements: number
}

function BooksPage() {
  const [books, setBooks] = useState<Book[]>([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  useEffect(() => {
    fetch(import.meta.env.VITE_BACK_URL + `/books?page=${page}&size=5&sort=id,asc`)
      .then(res => res.json())
      .then((json: BookPage) => {
        setBooks(json.content);
        setPage(json.number);
        setTotalPages(json.totalPages);
        setTotalElements(json.totalElements);
      })
  }, [page]);

  return (
    <div>
      <h2>Andmebaasi raamatud</h2>
      <div>Kokku: {totalElements}</div>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Pealkiri</th>
            <th>Autor</th>
            <th>Aasta</th>
          </tr>
        </thead>
        <tbody>
          {books.map(book =>
            <tr key={book.id}>
              <td>{book.id}</td>
              <td>{book.title}</td>
              <td>{book.author}</td>
              <td>{book.publishedYear}</td>
            </tr>)}
        </tbody>
      </table>

      <button disabled={page === 0} onClick={() => setPage(page - 1)}>Eelmine</button>
      <span>{totalPages === 0 ? 0 : page + 1} / {totalPages}</span>
      <button disabled={totalPages === 0 || page >= totalPages - 1} onClick={() => setPage(page + 1)}>Järgmine</button>
    </div>
  )
}

export default BooksPage
