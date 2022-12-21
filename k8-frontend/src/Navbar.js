import { useState } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import { PostReqAuth, TokenExpirationHandler } from "./PostReq";

const Navbar = () => {
  const loggedIn = window.localStorage.getItem("isLoggedIn");
  const logInfo = JSON.parse(window.localStorage.getItem("user"));
  const navigate = useNavigate();
  const [balance, setBalance] = useState(null);

  const logOut = () => {
    PostReqAuth("http://mkb.express.edlcn/api/v1/users/logout", logInfo)
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        if (data.status === "200" || data.status === "400:TOKEN EXPIRED!") {
          window.localStorage.removeItem("isLoggedIn");
          window.localStorage.removeItem("user");
          navigate("/login");
          data.status === "200"
            ? alert("Logout Successful.")
            : alert("Token expired. Please login again.");
        } else {
          alert("Logout failed.\n" + data.status);
        }
      });
  };
  /*
  //simdilik her yeni ekranda fetchliyor daha sonra state management ile çözülecek.
  const fetchBalance = () => {
    fetch("http://localhost:8080/api/v1/users/balance/" + logInfo.id, {
      method: "GET",
      headers: { "Content-Type": "application/json", token: logInfo.token },
    }).then(res => res.json()).then(data => {
      if (data.status == "200") {
        setBalance(data.returnObject);
        console.log("balance fetch succeed.");
        return;
      }
      console.log(data.status);
    });
  };

  if (loggedIn) fetchBalance();*/

  return (
    <nav className="navbar">
      <h1>MKB Express</h1>
      <div className="links">
        {loggedIn && <Link to="/payment">Payment</Link>}
        {loggedIn && <Link to="/myCards">My Cards</Link>}
        {loggedIn && <Link to="/myPayments">My Logs</Link>}
        {loggedIn && <Link to="/transaction">Transaction</Link>}
        {!loggedIn && <Link to="/register">Signup</Link>}
        {!loggedIn && <Link to="/login">Login</Link>}
        {loggedIn && <div className="balance">Account Balance {logInfo.balance}$</div>}
        {loggedIn && <button onClick={logOut}>Logout</button>}
      </div>
    </nav>
  );
};

export default Navbar;
