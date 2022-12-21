import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Runner from "@/views/Runner.vue";
import Escape from "@/views/Escape.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    name: "runner",
    path: "/runner",
    component: Runner,
  },
  {
    name: "escaped",
    path: "/escaped",
    component: Escape,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
