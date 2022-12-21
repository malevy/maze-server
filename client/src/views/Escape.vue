<template>
  <article>
    <Particles
      id="tsparticles"
      :options="particlesOptions"
      :particlesInit="particlesInit"
    />
    <h1 id="message">You've escaped!!</h1>
  </article>
</template>

<script>
import Vue from "vue";
import { loadFireworksPreset } from "tsparticles-preset-fireworks";
import Particles from "vue2-particles";

Vue.use(Particles);
export default {
  data() {
    return {
      particlesOptions: {
        preset: "fireworks",
      },
    };
  },
  methods: {
    particlesInit(engine) {
      return loadFireworksPreset(engine).then((x) => x);
    },
  },
};
</script>

<style scoped>
#message {
  color: white;
}
#tsparticles {
  position: fixed;
  z-index: -1;
}
</style>
