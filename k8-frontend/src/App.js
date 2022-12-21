import "./App.css";
import CardInput from "./CardInput";
import Login from "./Login";
import Navbar from "./Navbar";
import Register from "./Register";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MyCards from "./MyCards";
import Payment from "./Payment";
import AddCard from "./AddCard";
import { MyPayments } from "./MyPayments";
import CreateCard from "./CreateCard";
import Deposit from "./Deposit";
import Transaction from "./Transaction";
import { PaymentLogin } from "./PaymentLogin";
import { MerchantPayment } from "./MerchantPayment";

function App() {
  const loggedIn = window.localStorage.getItem("isLoggedIn");
  return (
    <Router>
      <div className="App">
        <Navbar />
        <div className="content">
          <Routes>
            <Route path="/login" element={<Login />} />

            <Route path="/register" element={<Register />} />

            <Route path="/payment" element={<Payment />} />

            <Route path="/myCards" element={<MyCards />} />

            <Route path="/addCard" element={<AddCard />} />

            <Route path="/makePayment" element={<MyCards />} />

            <Route path="/myPayments" element={<MyPayments />} />

            <Route path="/createCard" element={<CreateCard />} />

            <Route path="/deposit" element={<Deposit />} />

            <Route path="/transaction" element={<Transaction />} />

            <Route path="/paymentLogin/:token" element={<PaymentLogin/>} />

            <Route path="/merchantPayment/:token" element={<MerchantPayment/>} />


          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
