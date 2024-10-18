import { Link, Outlet } from "react-router-dom";
import logo from "./assets/karanlikayna.png";
import { LanguageSelector } from "./shared/components/LanguageSelector";
import { useTranslation } from "react-i18next";
import { NavBar } from "./shared/components/NavBar";
import { AuthenticationContext } from "./shared/state/context";

function App() {
  const [count, setCount] = useState(0);

  const { t } = useTranslation();

  const [authState, setAuthState] = useState({
    id: 0,
  });
  const onLoginSuccess = (data) => {
    setAuthState(data);
  };

  return (
    <>
      <AuthenticationContext> {/*AuthenticationContext ile alt komponentleri sarmaladık. Alt komponentler artık üstten veri alabilecek.*/}
        <NavBar />
        <div className="container mt-3">
          {/*
          React Router'da <Outlet /> bir "placeholder" gibidir. 
          Ana bileşenin içindeki alt sayfaların (nested routes) gösterileceği yerdir.
          Bir ana rota (parent route) varsa ve o rota başka alt rotalar içeriyorsa, bu alt rotaların içerikleri <Outlet /> ile gösterilir.
          Kısaca, alt bileşenlerin (child components) geleceği yerdir.
          */}
          <Outlet />
          <LanguageSelector />
        </div>
      </AuthenticationContext>
    </>
  );
}

export default App;