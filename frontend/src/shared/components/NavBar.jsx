import logo from "@/assets/karanlikayna.png";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { useAuthDispatch, useAuthState } from "../state/context";

export function NavBar() {
  const { t } = useTranslation();
  const authState = useAuthState();
  const dispatch = useAuthDispatch();

  const onClickLogout = () => {
    //authState.onLogoutSuccess();
    dispatch({type: 'logout-success'});
  }

  return (
    <nav className="navbar navbar-expand bg-body-tertiary shadow-sm">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/">
          <img src={logo} width={60} />
          Hoaxify
        </Link>
        <ul className="navbar-nav">
        {authState.id === 0 && ( //login olmama durumu
            <>
              <li className="nav-item">
                <Link className="nav-link" to="/Login">
                  {t("login")}
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/signup">
                  {t("signUp")}
                </Link>
              </li>
            </>
          )}
          {authState.id > 0 && ( //login olma durumu
            <>
              <li className="nav-item">
                <Link className="nav-link" to={`/user/${authState.id}`}>
                  Profilim
                </Link>
              </li>
              <li className="nav-item">
                <span className="nav-link" role="button" onClick={onClickLogout}>Çıkış</span>
              </li>
            </>
          )}
        </ul>
      </div>
    </nav>
  );
}
