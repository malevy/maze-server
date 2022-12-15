<template>
  <div class="home">
    <maze-list></maze-list>
  </div>
</template>

<script>
// @ is an alias to /src
import MazeList from "@/components/MazeList.vue";

export default {
  name: "Home",
  components: {
    MazeList,
  },
};
</script>
