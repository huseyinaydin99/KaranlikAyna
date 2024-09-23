import { createBrowserRouter } from "react-router-dom";
import { Home } from "../pages/Home";
import { SignUp } from "../pages/SignUp";

export default createBrowserRouter([
  {
    path: "/", //"*" böylede olabilirdi.
    Component: Home,
    errorElement: "<center><div><h2>404 Not Found<h2></div></center>"
  },
  {
    path: "/signup",
    Component: SignUp,
  },
]);
