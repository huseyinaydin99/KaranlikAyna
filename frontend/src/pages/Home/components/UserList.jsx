import { useCallback, useEffect, useState } from "react";
import { loadUsers } from "./api";
import { Spinner } from "@/shared/components/Spinner";
import { UserListItem } from "./UserListItem";

export function UserList() {
  const [userPage, setUserPage] = useState({
    content: [{ username: "test" }],
    last: false,
    first: false,
    number: 0,
  });

  const [apiProgress, setApiProgress] = useState(false);

  /*useEffect(() => {
    async function getUsers() {
      const response = await loadUsers();
      setUsers(response.data);
    }
    getUsers();
  }, []);*/

  const getUsers = useCallback(async (page) => {
    setApiProgress(true);
    try {
      const response = await loadUsers(page);
      setUserPage(response.data);
    } catch {
    } finally {
      setApiProgress(false);
    }
  }, []);

  useEffect(() => {
    getUsers();
  }, []);

  return (
    <>
      {/*
      <div>Kullanıcı Listesi</div>
      {userPage.content.map((user) => {
        return <div>{user.username}</div>;
      })}
      {!userPage.first && (
        <button onClick={() => getUsers(userPage.number - 1)}>Previous</button>
      )}
      {!userPage.last && (
        <button onClick={() => getUsers(userPage.number + 1)}>Next</button>
      )}
      */}
      <div className="card">
        <div className="card-header text-center fs-4">Kullanıcı Listesi</div>
        <ul className="list-group list-group-flush">
          {userPage.content.map((user) => {
            return <UserListItem key={user.id} user={user}/>;
          })}
        </ul>
        <div className="card-footer text-center">
          {apiProgress && <Spinner />}
          {!apiProgress && !userPage.first && (
            <button
              className="btn btn-outline-secondary btn-sm float-start"
              onClick={() => getUsers(userPage.number - 1)}
            >
              Önceki
            </button>
          )}
          {!apiProgress && !userPage.last && (
            <button
              className="btn btn-outline-secondary btn-sm float-end"
              onClick={() => getUsers(userPage.number + 1)}
            >
              Sonraki
            </button>
          )}
        </div>
      </div>
    </>
  );
}
