<template>
  <section id="runner">
    <maze-runner></maze-runner>
  </section>
</template>

<script>
import MazeRunner from "@/components/MazeRunner.vue";
export default {
  components: {
    MazeRunner,
  },
};
</script>

<style scoped>
#runner {
  display: flex;
  justify-content: center;
}
</style>
