const {defineConfig} = require('@vue/cli-service')

module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false,
    devServer: {
        proxy: {
            '/': {
                ws: false, // 这里把ws代理给关闭
                target: 'http://127.0.0.1:8888',
                changeOrigin: true
            }
        }
    }
})
