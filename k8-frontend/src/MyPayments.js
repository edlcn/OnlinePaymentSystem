import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export const MyPayments = () => {
  const logInfo = JSON.parse(window.localStorage.getItem("user"));

  const [payments, setPayments] = useState(null);
  const [transactions, setTransactions] = useState(null);
  const [exPayments,setExPayments] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://mkb.express.edlcn/api/v1/payment?uid=" + logInfo.id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        sessionId: logInfo.sessionId,
        id: logInfo.id,
      },
   
   
   
   
   
    
    
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "200") {
          console.log(data.returnObject);
          setPayments(data.returnObject);
        } else if (data.status == "400:TOKEN EXPIRED!") {
          window.localStorage.removeItem("isLoggedIn");
          window.localStorage.removeItem("user");
          navigate("/login");
          alert("Token expired, please login again.");
        } else {
          alert("Payment data could not be loaded.\n" + data.status);
        }
      });

    fetch("http://mkb.express.edlcn/api/v1/merchantPayment?uid=" +logInfo.id,{
      method: "GET",
      headers: {"Content-Type":"application/json",id:logInfo.id,sessionId:logInfo.sessionId}

    }).then(res => res.json()).then(data => {
      if (data.status == "200") {
        
        setExPayments(data.returnObject);
      } else if (data.status == "400:TOKEN EXPIRED!") {
        window.localStorage.removeItem("isLoggedIn");
        window.localStorage.removeItem("user");
        navigate("/login");
        alert("Token expired, please login again.");
      } else {
        alert("Transaction data could not be loaded.\n" + data.status);
      }
    })
    fetch("http://mkb.express.edlcn/api/v1/transaction?uid=" +logInfo.id,{
      method: "GET",
      headers: {"Content-Type":"application/json",id:logInfo.id,sessionId:logInfo.sessionId}

    }).then(res => res.json()).then(data => {
      if (data.status == "200") {
        
        setTransactions(data.returnObject);
      } else if (data.status == "400:TOKEN EXPIRED!") {
        window.localStorage.removeItem("isLoggedIn");
        window.localStorage.removeItem("user");
        navigate("/login");
        alert("Token expired, please login again.");
      } else {
        alert("Transaction data could not be loaded.\n" + data.status);
      }
    })

  }, []);

  return (
    <div className="my-payments">
      <table className="payment-list">
        <th>Payment ID</th>
        <th>Price</th>
        <th>Credit Card Number</th>
        <th>Date</th>
        {payments &&
          payments.map((payment) => {
            return (
              <tr className="payment-preview" key={payment.id}>
                <td>{payment.id}</td>
                <td>{payment.price}</td>
                <td>{payment.number}</td>
                <td>{payment.timeAt.replace("T","    ")}</td>
              </tr>
            );
          })}
      </table>
      <table>
        <th>Transaction ID</th>
        <th>Amount</th>
        <th>Sender</th>
        <th>Receiver</th>
        <th>Date</th>
        {transactions &&
          transactions.map((transaction) => {
            return (
              <tr className="payment-preview" key={transaction.transactionID}>
                <td>{transaction.id}</td>
                <td>{transaction.amount}</td>
                <td>{transaction.senderId}</td>
                <td>{transaction.receiverId}</td>
                <td>{transaction.timeAt.replace("T","    ")}</td>
              </tr>
            );
          })}
      </table>
      <table>
        <th>Payment ID</th>
        <th>From</th>
        <th>Amount</th>
        <th>Date</th>
        
        {exPayments &&
          exPayments.map((exp) => {
            return (
              <tr className="payment-preview" key={exp.transactionID}>
                <td>{exp.id}</td>
                <td>{exp.name}</td>
                <td>{exp.amount}</td>
                <td>{exp.date.replace("T","    ")}</td>
                
              </tr>
            );
          })}
      </table>
    </div>
  );
};
