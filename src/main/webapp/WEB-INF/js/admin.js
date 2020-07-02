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
            await this.getPassiveOrders();
        },
        methods: {
            async getActiveOrders() {
                let res = await axios.get('/admin/active-orders');
                if (!res) return;
                this.activeOrders = res.data;
            },
            async getPassiveOrders() {
                let res = await axios.get('/admin/passive-orders');
                if (!res) return;
                this.passiveOrders = res.data;
            },
            async permitOrder(order) {
                let res = await axios.put('/admin/permit', order)
                    .then((response) => {
                        alert(response.data.message);
                    })
                    .catch((error) => {
                        alert(error.response.data.error);
                    });
                if (!res) return;
                await this.getActiveOrders();
                await this.getPassiveOrders();
            },
        }
    });
}