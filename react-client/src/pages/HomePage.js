import React from "react";
import "./HomePage.css";
import mazeServer from "../gateways/mazeserver.js";
import { useStore } from "../contexts/Store";
import { useNavigate } from "react-router-dom";
import LoadingIndicator from "../components/LoadingIndicator";

function HomePage() {
  const [mazes, setMazes] = React.useState([]);
  const [loading, setLoading] = React.useState(false);
  const { storeNavigation } = useStore();
  const navigate = useNavigate();

  React.useEffect(() => {
    async function loadMazes() {
      const allMazes = await mazeServer.getMazeList();
      setMazes(allMazes);
      setLoading(false);
    }
    setLoading(true);
    loadMazes();
  }, []);

  async function enterMaze(maze) {
    setLoading(true);
    const mazeStart = await mazeServer.getStartUrl(maze.href);
    const startCell = await mazeServer.goToCell(mazeStart.href);
    storeNavigation(startCell, "south");
    navigate("/runner");
    setLoading(false);
  }

  function mazeToRow(maze) {
    return (
      <tr key={maze.href}>
        <td>
          <button className="link-button" onClick={() => enterMaze(maze)}>
            {maze.name}
          </button>
        </td>
      </tr>
    );
  }

  const renderMazeList = () => {
    return (
      <section className="maze-list">
        <table>
          <thead>
            <tr>
              <th>available mazes</th>
            </tr>
          </thead>
          <tbody>{mazes.map(mazeToRow)}</tbody>
        </table>
      </section>
    );
  };

  return (
    <div className="home">
      {loading && <LoadingIndicator id="loadingIndicator" />}
      {!loading && renderMazeList()}
    </div>
  );
}

export default HomePage;
