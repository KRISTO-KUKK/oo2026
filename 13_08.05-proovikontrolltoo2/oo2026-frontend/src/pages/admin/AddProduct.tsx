import { useEffect, useState } from "react"
import type { FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import type { Category } from "../../models/Category";
import type { Product } from "../../models/Product";

const emptyProduct: Product = {
  name: "",
  description: "",
  price: 0,
  active: true,
  stock: 0
}

function AddProduct() {
  const [product, setProduct] = useState<Product>(emptyProduct);
  const [categories, setCategories] = useState<Category[]>([]);
  const [categoryId, setCategoryId] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    fetch(import.meta.env.VITE_BACK_URL + "/categories")
      .then(res => res.json())
      .then(json => setCategories(json))
  }, []);

  const saveProduct = (event: FormEvent) => {
    event.preventDefault();
    const category = categories.find(c => String(c.id) === categoryId);
    fetch(import.meta.env.VITE_BACK_URL + "/products", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ ...product, category })
    }).then(() => navigate("/manage-products"));
  }

  return (
    <form onSubmit={saveProduct}>
      <label>Name</label><br />
      <input value={product.name} onChange={e => setProduct({ ...product, name: e.target.value })} /><br />

      <label>Description</label><br />
      <input value={product.description} onChange={e => setProduct({ ...product, description: e.target.value })} /><br />

      <label>Price</label><br />
      <input type="number" value={product.price} onChange={e => setProduct({ ...product, price: Number(e.target.value) })} /><br />

      <label>Stock</label><br />
      <input type="number" value={product.stock} onChange={e => setProduct({ ...product, stock: Number(e.target.value) })} /><br />

      <label>Category</label><br />
      <select value={categoryId} onChange={e => setCategoryId(e.target.value)}>
        <option value="">No category</option>
        {categories.map(category => <option key={category.id} value={category.id}>{category.name}</option>)}
      </select><br />

      <label>
        <input type="checkbox" checked={product.active} onChange={e => setProduct({ ...product, active: e.target.checked })} />
        Active
      </label><br />

      <button type="submit">Save</button>
    </form>
  )
}

export default AddProduct
