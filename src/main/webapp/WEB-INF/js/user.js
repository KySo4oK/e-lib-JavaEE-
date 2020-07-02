window.onload = function () {
    let app = new Vue({
        el: '#app',
        data: {
            activeOrders: [],
            passiveOrders: [],
            picked: '',
        },
        async mounted() {
            await this.getActiveOrders();
        },
        methods: {
            async getActiveOrders() {
                let res = await axios.get('/user/active-orders');
                if (!res) return;
                this.activeOrders = res.data;
            },
            async returnBook(order) {
                let res = await axios.put('/user/return', order)
                    .then((response) => {
                        alert(response.data.message);
                    })
                    .catch((error) => {
                        alert(error.response.data.error);
                    });
                if (!res) return;
                await this.getActiveOrders();
            },
        }
    });
}