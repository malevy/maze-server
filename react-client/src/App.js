import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import HomePage from "./pages/HomePage";
import Runner from "./pages/Runner";
import { StoreProvider } from "./contexts/Store.js";

function App() {
  return (
    <div className="App">
      <StoreProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<HomePage />}></Route>
            <Route path="/runner" element={<Runner />}></Route>
          </Routes>
        </BrowserRouter>
      </StoreProvider>
    </div>
  );
}

export default App;
