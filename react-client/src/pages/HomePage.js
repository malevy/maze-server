import React from "react";
import "./HomePage.css";
import mazeServer from "../gateways/mazeserver.js";
import { useStore } from "../contexts/Store";
import { useNavigate } from "react-router-dom";

function HomePage() {
  const [mazes, setMazes] = React.useState([]);
  const { storeNavigation } = useStore();
  const navigate = useNavigate();

  React.useEffect(() => {
    async function loadMazes() {
      const allMazes = await mazeServer.getMazeList();
      setMazes(allMazes);
    }
    loadMazes();
  }, []);

  async function enterMaze(maze) {
    const mazeStart = await mazeServer.getStartUrl(maze.href);
    const startCell = await mazeServer.goToCell(mazeStart.href);
    storeNavigation(startCell, "south");
    navigate("/runner");
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

  return (
    <div className="home">
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
    </div>
  );
}

export default HomePage;
