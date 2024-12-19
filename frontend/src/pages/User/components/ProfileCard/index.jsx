import { Button } from "@/shared/components/Button";
import { useAuthState } from "@/shared/state/context";
import { UserEditForm } from "./UserEditForm";
import { UserDeleteButton } from "./UserDeleteButton";

export function ProfileCard({ user }) {
  //const authState = useContext(AuthContext);
  // const authState = useAuthState();
  const authState = useSelector((store) => store.auth);

  const [editMode, setEditMode] = useState(false);
  const [tempImage, setTempImage] = useState();

  //const isEditButtonVisible = !editMode && authState.id === user.id;
  const visibleUsername =
    authState.id === user.id ? authState.username : user.username;

  const isLoggedInUser = !editMode && authState.id === user.id;

  return (
    <div className="card">
      <div className="card-header text-center">
        <ProfileImage width={200} tempImage={tempImage} image={user.image} />
      </div>
      <div className="card-body text-center">
        {!editMode && <span className="fs-3 d-block">{visibleUsername}</span>}
        {isLoggedInUser && (
          <>
            <Button onClick={() => setEditMode(true)}>Düzenle</Button>
            <div className="d-inline m-1"></div>
            <UserDeleteButton />
          </>
        )}
        {editMode && (
          <UserEditForm setEditMode={setEditMode} setTempImage={setTempImage} />
        )}
      </div>
    </div>
  );
}