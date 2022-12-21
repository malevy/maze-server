<template>
  <section class="maze-list">
    <table>
      <thead>
        <th>maze</th>
      </thead>
      <tbody>
        <tr v-for="maze in mazes" :key="maze.href">
          <td>
            <a href="#" @click.prevent="enterMaze(maze.href)">{{
              maze.name
            }}</a>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script>
import mazeserver from "@/gateways/mazeserver.js";
import store from "@/store";

export default {
  name: "maze-list",
  data() {
    return {
      mazes: [],
    };
  },
  methods: {
    enterMaze(url) {
      mazeserver
        .getStartUrl(url)
        .then((link) => mazeserver.goToCell(link.href))
        .then((cell) => {
          store.setCurrentCell(cell);
          store.setComingFrom("south");
          this.$router.push({ name: "runner" });
        });
    },
  },
  created() {
    mazeserver.getMazeList().then((mazes) => {
      this.mazes = mazes;
    });
  },
};
</script>

<style scoped></style>
