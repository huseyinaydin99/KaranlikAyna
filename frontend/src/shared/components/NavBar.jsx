import logo from "@/assets/karanlikayna.png";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { useAuthDispatch, useAuthState } from "../state/context";
import { useDispatch, useSelector } from "react-redux";
import { logoutSuccess } from "../state/redux";
import { ProfileImage } from "./ProfileImage";
import { logout } from "./api";

export function NavBar() {
  const { t } = useTranslation();
  // const authState = useAuthState();
  // const dispatch = useAuthDispatch();
  const authState = useSelector((store) => store.auth);
  /*
   const authState = useSelector((store) => store.auth);
   ifadesi, Redux store'daki auth dilimindeki (slice) durumu alır ve bu durumu authState değişkenine atar. Yani, Redux store'da bulunan kullanıcı oturum durumunu (örneğin, kullanıcı bilgileri) React bileşeni içinde kullanmanı sağlar.
  
  Yani istediğimiz alt veya üst komponentden drill yapmadan redux storeden useSelector ile kimlik bilgileri çekilebilir
   */
  const dispatch = useDispatch();

  const onClickLogout = async () => {
    //authState.onLogoutSuccess();
    //dispatch({type: 'logout-success'});
    //dispatch(logoutSuccess());
    try {
      await logout();
    } catch {
    } finally {
      dispatch({ type: "logout-success" });
    }
  };

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
                  <ProfileImage width={30} image={authState.image} />
                  <span className="ms-1">{authState.username}</span>
                </Link>
              </li>
              <li className="nav-item">
                <span
                  className="nav-link"
                  role="button"
                  onClick={onClickLogout}
                >
                  Çıkış
                </span>
              </li>
            </>
          )}
        </ul>
      </div>
    </nav>
  );
}
