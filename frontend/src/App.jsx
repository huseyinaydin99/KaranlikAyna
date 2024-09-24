import { Link, Outlet } from "react-router-dom";
import logo from "./assets/karanlikayna.png";
import { LanguageSelector } from "./shared/components/LanguageSelector";
import { useTranslation } from "react-i18next";
import { NavBar } from "./shared/components/NavBar";

function App() {
  const [count, setCount] = useState(0)

  const { t } = useTranslation();

  return (
    <>
      <NavBar />
      <div className="container mt-3">
        <Outlet />
        {
          /*
          React Router'da <Outlet /> bir "placeholder" gibidir. 
          Ana bileşenin içindeki alt sayfaların (nested routes) gösterileceği yerdir. 
          Bir ana rota (parent route) varsa ve o rota başka alt rotalar içeriyorsa, bu alt rotaların içerikleri <Outlet /> ile gösterilir.
          Kısaca, alt bileşenlerin (child components) geleceği yerdir.
          */
        }
        <LanguageSelector />
      </div>
    </>
  )
}

export default App
