import { Link, Route, Routes } from "react-router-dom"
import HomePage from "./pages/HomePage"
import ManageProducts from "./pages/admin/ManageProducts"
import ManageCategories from "./pages/admin/ManageCategories"
import EditProduct from "./pages/admin/EditProduct"
import AddProduct from "./pages/admin/AddProduct"
import Cart from "./pages/Cart"

function App() {
  return (
    <>
    <Link to="/">
      <button>Avalehele</button>
    </Link>

    <Link to="/cart">
      <button>Ostukorvi</button>
    </Link>

    <Link to="/add-product">
      <button>Lisa toode</button>
    </Link>

    <Link to="/manage-products">
      <button>Manage products</button>
    </Link>

    <Link to="/manage-categories">
      <button>Manage categories</button>
    </Link>

    <Routes>
      <Route path="" element={ <HomePage /> } />
      <Route path="/cart" element={ <Cart /> } />
      <Route path="/add-product" element={ <AddProduct /> } />
      <Route path="/edit-product/:id" element={ <EditProduct /> } />
      <Route path="/manage-categories" element={ <ManageCategories /> } />
      <Route path="/manage-products" element={ <ManageProducts /> } />
    </Routes>
    </>
  )
}

export default App
