import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { PostReqAuth } from "./PostReq";

const Transaction = () => {
  const logInfo = JSON.parse(window.localStorage.getItem("user"));
  const [mail, setMail] = useState("");
  const [amount, setAmount] = useState(0.0);
  const navigate = useNavigate();

  const handleTransaction = () => {
    const toBeSent = { senderId: logInfo.id, receiverMail: mail, amount };
    PostReqAuth("http://mkb.express.edlcn/api/v1/transaction", toBeSent)
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
          alert("Transaction Succeed.");
          logInfo.balance  -= amount;
          console.log(logInfo.balance);
          window.localStorage.setItem("user",JSON.stringify(logInfo));
          navigate("/myPayments");
        }
      });
  };

  return (
    <div className="formf">
      <form>
        <label>Receiver Mail Adress</label>
        <input
          type="text"
          required
          value={mail}
          onChange={(event) => setMail(event.target.value)}
        />
        <label>Amount</label>
        <input
          type="number"
          required
          value={amount}
          onChange={(event) => setAmount(event.target.value)}
        />
      </form>
      <button onClick={handleTransaction}>SEND</button>
    </div>
  );
};

export default Transaction;
