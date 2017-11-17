const path = require('path');
const webpack = require("webpack");
const merge = require('webpack-merge');

const TARGET = process.env.npm_lifecycle_event;
const PATHS = {
  source: path.join(__dirname, 'app'),
  output: path.join(__dirname, '../../../target/classes/static')
};

const common = {
  entry: [
    PATHS.source
  ],
  output: {
    path: PATHS.output,
    publicPath: '',
    filename: 'bundle.js'
  },
  module: {
    loaders: [{
      exclude: /node_modules/,
      loader: 'babel'
    }, {
      test: /\.(png|jpg)$/,
      loader: 'url?limit=25000'
    }, {
      test: /\.css$/,
      loader: 'style!css'
    }]
  },
  resolve: {
    extensions: ['', '.js', '.jsx']
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': {
        URL: JSON.stringify(process.env.URL)
      }
    })
  ]
};

if (TARGET === 'start' || !TARGET) {
  module.exports = merge(common, {
    devServer: {
      port: 9090,
      proxy: {
        '/': {
          target: 'http://localhost:8080',
          secure: false,
          prependPath: false
        }
      },
      publicPath: 'http://localhost:9090/',
      historyApiFallback: true
    },
    devtool: 'source-map'
  });
}

if (TARGET === 'build') {
  module.exports = merge(common, {});
}


