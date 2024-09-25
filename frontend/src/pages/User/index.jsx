import { Component } from "react";
import { useParams } from "react-router-dom";
import { getUser } from "./api";
import { Alert } from "@/shared/components/Alert";
import { Spinner } from "@/shared/components/Spinner";
import { withTranslation } from "react-i18next";
import { useRouteParamApiRequest } from "@/shared/hooks/useRouteParamApiRequest";
import { ProfileCard } from "./components/ProfileCard";

/*
export class UserClass extends Component {
  state = {
    user: null,
    apiProgress: false,
    error: null,
  };

  //async componentDidMount()
  loadUser = async () => {
    this.setState({ apiProgress: true });
    try {
      const response = await getUser(this.props.id);
      this.setState({
        user: response.data,
      });
    } catch (axiosError) {
      this.setState({
        error: this.props.t('userNotFoundError')
      });
    } finally {
      this.setState({ apiProgress: false });
    }
  }

  async componentDidMount() { //component ekranda gösterildiğinde.
    this.loadUser();
  }

  componentDidUpdate(previousProps, previousState){ //component'de değişiklik olduğunda.
    //şu anki Id ile bir önceki Id farklıysa kullanıcı verilerini yükle.
    if(this.props.id !== previousProps.id) { //User component ilk açıldığında componentDidMount bir defa çalışıyor. 2. kullanıcı için açıldığında tekrar çalışmıyor dolayısıyla 2. kullanıcının verileri gelmiyor.
        this.loadUser(); //bu yüzden component her güncellendiğinde kullanıcının verileri gelsin diye kullandık.
    }
  }

  componentWillUnmount(){ //component çıkarıldığında.

  }

  render() {
    return (
      <>
        {this.state.user && <h1>{this.state.user.username}</h1>}
        {this.state.apiProgress && (
          <Alert styleType="secondary" center>
            <Spinner />
          </Alert>
        )}
        {this.state.error && (
          <Alert styleType="danger">{this.state.error}</Alert>
        )}
      </>
    );
  }
}

const UserPageWithTranslation = withTranslation()(UserClass)

export function User() {
  const { id } = useParams();
  //return <UserClass id={id} />;
  return <UserPageWithTranslation id={id} />;
}
*/

export function User() {
  const {
    apiProgress,
    data: user, //data'ya takma isim verdik.
    error,
  } = useRouteParamApiRequest("id", getUser);

  return (
    <>
      {apiProgress && (
        <Alert styleType="secondary" center>
          <Spinner />
        </Alert>
      )}
      {user && <ProfileCard user={user} />}
      {error && <Alert styleType="danger">{error}</Alert>}
    </>
  );
}
