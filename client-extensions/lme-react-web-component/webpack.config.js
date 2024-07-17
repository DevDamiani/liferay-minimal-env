import path from "path";
import fs from "fs";
import { fileURLToPath } from 'url';
import webpack from 'webpack';
import { Component } from "react";

const __dirname = path.dirname(fileURLToPath(import.meta.url));

export default {
    mode: 'development',
    entry: () => {

        const relativePath = './src/components/'
        const files = fs.readdirSync(relativePath, { recursive: true });

        const filesFiltered = files.filter(file => {
            if (file.includes(".web-component.tsx"))
                return file;
        }).map((file) => {
            return path.join(__dirname, relativePath, file)
        });

        return filesFiltered;
    },
    devServer: {
        headers: {
			'Access-Control-Allow-Origin': '*',
		},
        port: 8081,
        hot: true,
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
        ],
    },
    resolve: {
        extensions: [".ts", ".tsx", ".js"],
        alias: {
            "@components": path.resolve(__dirname, 'src/components/')
        }
    },
    output: {
        path: path.resolve("build/assets"),
        filename: '[name].bundle.js',
        clean: true,
    },
    plugins: [
		new webpack.optimize.LimitChunkCountPlugin({
			maxChunks: 1,
		}),
	],
}
