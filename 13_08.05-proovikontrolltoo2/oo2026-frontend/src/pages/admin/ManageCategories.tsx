import { useEffect, useState } from "react";
import type { Category } from "../../models/Category";

function ManageCategories() {
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    fetch(import.meta.env.VITE_BACK_URL + "/categories")
      .then(res => res.json())
      .then(json => setCategories(json))
  }, []);

  const deleteCategory = (categoryId: number) => {
    fetch(import.meta.env.VITE_BACK_URL + "/categories/" + categoryId, {
      method: "DELETE"
    })
      .then(res => res.json())
      .then(json => setCategories(json));
  }

  return (
    <div>
      <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            {categories.map(category =>
                <tr key={category.id}>
                    <td>{category.id}</td>
                    <td>{category.name}</td>
                    <td><button onClick={() => deleteCategory(Number(category.id))}>X</button></td>
                </tr>)}
        </tbody>
      </table>
    </div>
  )
}

export default ManageCategories
