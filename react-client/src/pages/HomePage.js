import React from "react";
import "./HomePage.css";
import mazeServer from "../gateways/mazeserver.js";
import { useStore } from "../contexts/Store";

function HomePage() {
  const [mazes, setMazes] = React.useState([]);
  const [selectedMaze, setSelectedMaze] = React.useState(null);
  const { storeNavigation } = useStore();

  React.useEffect(() => {
    async function loadMazes() {
      const allMazes = await mazeServer.getMazeList();
      console.log(allMazes);
      setMazes(allMazes);
    }
    loadMazes();
  }, []);

  React.useEffect(() => {
    async function enterMaze() {
      const mazeStart = await mazeServer.getStartUrl(selectedMaze.href);
      const startCell = await mazeServer.goToCell(mazeStart.href);
      storeNavigation(startCell, "south");
    }
    if (selectedMaze) enterMaze();
  }, [selectedMaze, storeNavigation]);

  function mazeToRow(maze) {
    return (
      <tr key={maze.href}>
        <td>
          <button className="link-button" onClick={() => setSelectedMaze(maze)}>
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
