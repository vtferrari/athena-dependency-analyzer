const path = require('path');
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
        rules: [
          {
            test: /\.js$/,
            exclude: /(node_modules|bower_components)/,
            use: {
              loader: 'babel-loader'
            }
          },
          {
            test: /\.(png|jpg)$/,
            use: {
              loader: 'url-loader',
              options: {
                limit: 25000
              }
            }
          },
          {
            test: /\.css$/,
            use: [
              {loader: "style-loader"},
              {loader: "css-loader"}
            ]
          }]
      },
      resolve: {
        extensions: ['.js', '.jsx']
      }
    }
;

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


