import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { PostReqAuth } from "./PostReq";

const Deposit = () => {
  const logInfo = JSON.parse(window.localStorage.getItem("user"));
  const [amount, setAmount] = useState(0.0);
  const { state } = useLocation();
  const { card } = state;
  const navigate = useNavigate();

  const handleDeposit = () => {
    const toBeSent = { card, amount, depositorId: logInfo.id };
    PostReqAuth("http://mkb.express.edlcn/api/v1/deposit", toBeSent)
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "400:TOKEN EXPIRED!") {
          window.localStorage.removeItem("isLoggedIn");
          window.localStorage.removeItem("user");
          navigate("/login");
          alert("Token expired, please login again.");
        }
        else if (data.status != "200") alert(data.status);
        else {
          alert("Deposit Succeed!");
          logInfo.balance  +=  parseFloat(amount);
          console.log(logInfo.balance);
          window.localStorage.setItem("user",JSON.stringify(logInfo));
          navigate("/myPayments");
        }
      });
  };

  return (
    <div className="formf">
      <form>
        <label>Deposit Amount</label>
        <input
          type="number"
          required
          value={amount}
          onChange={(event) => setAmount(event.target.value)}
        />
      </form>
      <button onClick={handleDeposit}>DEPOSIT</button>
    </div>
  );
};

export default Deposit;
