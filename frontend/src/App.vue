<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const API = "/api/users";

const name = ref("");
const email = ref("");
const users = ref([]);
const error = ref("");

async function loadUsers() {
  const res = await axios.get(API);
  users.value = res.data;
}

async function submit() {
  error.value = "";
  try {
    const res = await axios.post(API, {
      name: name.value,
      email: email.value,
    });
    users.value.unshift(res.data);
    name.value = "";
    email.value = "";
  } catch (e) {
    error.value =
      e?.response?.data || "Failed to save user. Check backend logs.";
  }
}

onMounted(loadUsers);
</script>

<template>
  <main style="max-width: 640px; margin: 2rem auto; font-family: sans-serif;">
    <h1>Create User</h1>

    <form @submit.prevent="submit" style="display: grid; gap: 0.5rem;">
      <input v-model="name" placeholder="Name" />
      <input v-model="email" placeholder="Email" />
      <button type="submit">Save</button>
      <p v-if="error" style="color: red;">{{ error }}</p>
    </form>

    <hr style="margin: 1.5rem 0;" />

    <h2>Users</h2>
    <ul>
      <li v-for="u in users" :key="u.id">
        {{ u.name }} — {{ u.email }}
      </li>
    </ul>
  </main>
</template>
