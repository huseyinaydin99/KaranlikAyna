import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
//import App from './App.jsx'
import "./index.css";
import { SignUp } from "./pages/SignUp/index.jsx";
import "./styles.scss";
import "./locales";
import { RouterProvider } from "react-router-dom";
import router from "./router/index.js";

createRoot(document.getElementById("root")).render(
  <RouterProvider router={router} />
);
