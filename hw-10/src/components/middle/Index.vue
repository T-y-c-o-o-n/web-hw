<template>
    <div class="posts">
        <MainPagePost v-for="postAndCount in Object.values(postsAndCommentCounts).sort((a, b) => b.post.id - a.post.id)"
              :post="postAndCount.post" :user="users[postAndCount.post.userId]"
              :commentCount="postAndCount.commentCount" :key="postAndCount.post.id"/>
    </div>
</template>

<script>
import MainPagePost from "@/components/middle/MainPagePost";

export default {
    name: "Index",
    props: ["users", "postsAndCommentCounts"],
    components: {MainPagePost}
}
</script>

<style scoped>

</style>