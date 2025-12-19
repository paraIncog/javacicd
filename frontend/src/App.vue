<template>
  <main style="max-width: 480px; margin: 40px auto; font-family: sans-serif;">
    <h1>Create User</h1>

    <form @submit.prevent="submit">
      <label>
        Name
        <input v-model="form.name" required />
      </label>

      <label style="display:block; margin-top: 12px;">
        Email
        <input v-model="form.email" type="email" required />
      </label>

      <button style="margin-top: 16px;">Save</button>
    </form>

    <p v-if="error" style="color: red;">{{ error }}</p>

    <h2 style="margin-top: 28px;">Stored Users</h2>
    <ul>
      <li v-for="u in users" :key="u.id">{{ u.name }} — {{ u.email }}</li>
    </ul>
  </main>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const API = "http://localhost:8080/api/users";

const form = ref({ name: "", email: "" });
const users = ref([]);
const error = ref("");

async function load() {
  const res = await axios.get(API);
  users.value = res.data;
}

async function submit() {
  error.value = "";
  try {
    await axios.post(API, form.value);
    form.value = { name: "", email: "" };
    await load();
  } catch (e) {
    error.value = e?.response?.data?.message || "Failed to save user";
  }
}

onMounted(load);
</script>
