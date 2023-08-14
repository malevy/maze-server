import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import HomePage from "./pages/HomePage";
import RunnerPage from "./pages/Runner";
import EscapedPage from "./pages/Escape";
import { StoreProvider } from "./contexts/Store.js";

function App() {
  return (
    <div className="App">
      <StoreProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<HomePage />}></Route>
            <Route path="/runner" element={<RunnerPage />}></Route>
            <Route path="/escaped" element={<EscapedPage />}></Route>
          </Routes>
        </BrowserRouter>
      </StoreProvider>
    </div>
  );
}

export default App;
