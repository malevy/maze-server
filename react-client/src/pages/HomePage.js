import React from "react";
import "./HomePage.css";
import mazeServer from "../gateways/mazeserver.js";

function HomePage() {
  const [mazes, setMazes] = React.useState([]);

  React.useEffect(() => {
    async function loadMazes() {
      const allMazes = await mazeServer.getMazeList();
      console.log(allMazes);
      setMazes(allMazes);
    }
    loadMazes();
  }, []);

  function mazeToRow(maze) {
    return (
      <tr key={maze.href}>
        <td>{maze.name}</td>
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
