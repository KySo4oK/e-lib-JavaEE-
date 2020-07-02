window.onload = function () {
    let app = new Vue({
        el: '#app',
        data: {
            passiveOrders: [],
            picked: '',
        },
        async mounted() {
            await this.getPassiveOrders();
        },
        methods: {
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
                await this.getPassiveOrders();
            },
        }
    });
}