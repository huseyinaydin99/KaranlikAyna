import { Link, Outlet } from "react-router-dom";
import logo from "./assets/karanlikayna.png";
import { LanguageSelector } from "./shared/components/LanguageSelector";
import { useTranslation } from "react-i18next";

function App() {
  const [count, setCount] = useState(0)

  const { t } = useTranslation();

  return (
    <>
      <nav className="navbar navbar-expand bg-body-tertiary shadow-sm">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/">
            <img src={logo} width={60} />
            Karanlık Ayna Platformu
          </Link>
          <ul className="navbar-nav">
            <li className="nav-item">
              <Link className="nav-link" to="/signup">{t('signUp')}</Link>
            </li>
          </ul>
        </div>
      </nav>
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
