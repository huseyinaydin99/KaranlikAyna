import defaultProfileImage from "@/assets/profile.png";
import { Button } from "@/shared/components/Button";
import { useAuthState } from "@/shared/state/context";
import { useSelector } from "react-redux";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { updateUser } from "./api";
import { Input } from "@/shared/components/Input";
import { useAuthDispatch, useAuthState } from "@/shared/state/context";
import { Alert } from "@/shared/components/Alert";

export function ProfileCard({ user }) {
  //const authState = useContext(AuthContext);
  // const authState = useAuthState();
  const authState = useSelector((store) => store.auth);

  const [editMode, setEditMode] = useState(false);
  const { t } = useTranslation();
  const [newUsername, setNewUsername] = useState(authState.username);
  const [apiProgress, setApiProgress] = useState(false);

  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState();
  const dispatch = useAuthDispatch();

  const onChangeUsername = (event) => {
    setNewUsername(event.target.value);
    setErrors({});
  };

  const onClickCancel = () => {
    setEditMode(false);
    setNewUsername(authState.username);
  };

  const onClickSave = async () => {
    setApiProgress(true);
    setErrors({});
    setGeneralError();
    try {
      await updateUser(user.id, { username: newUsername });
      dispatch({
        type: "user-update-success",
        data: { username: newUsername },
      });
      setEditMode(false);
    } catch (axiosError) {
      if (axiosError.response?.data) {
        if (axiosError.response.data.status === 400) {
          setErrors(axiosError.response.data.validationErrors);
        } else {
          setGeneralError(axiosError.response.data.message);
        }
      } else {
        setGeneralError(t("genericError"));
      }
    } finally {
      setApiProgress(false);
    }
  };

  const isEditButtonVisible = !editMode && authState.id === user.id;
  const visibleUsername =
    authState.id === user.id ? authState.username : user.username;

  return (
    <div className="card">
      <div className="card-header text-center">
        <img
          src={defaultProfileImage}
          width="200"
          className="img-fluid rounded-circle shadow-sm"
        />
      </div>
      <div className="card-body text-center">
        {!editMode && <span className="fs-3 d-block">{visibleUsername}</span>}
        {isEditButtonVisible && (
          <Button onClick={() => setEditMode(true)}>Edit</Button>
        )}
        {editMode && (
          <>
            <Input
              label={t("username")}
              defaultValue={visibleUsername}
              onChange={onChangeUsername}
            />
            {generalError && <Alert styleType="danger">{generalError}</Alert>}
            <Button apiProgress={apiProgress} onClick={onClickSave}>
              Kaydet
            </Button>
            <div className="d-inline m-1"></div>
            <Button styleType="outline-secondary" onClick={onClickCancel}>
              İptal
            </Button>
          </>
        )}
      </div>
    </div>
  );
}
