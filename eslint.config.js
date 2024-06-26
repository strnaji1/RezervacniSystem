const { Linter } = require('eslint');
const vuePlugin = require('eslint-plugin-vue');
const typescriptPlugin = require('@typescript-eslint/eslint-plugin');
const typescriptParser = require('@typescript-eslint/parser');

module.exports = {
    root: true,
    env: {
        node: true,
    },
    extends: [
        'plugin:vue/vue3-essential',
        'standard',
        'plugin:@typescript-eslint/recommended',
    ],
    parser: typescriptParser,
    parserOptions: {
        ecmaVersion: 2020,
    },
    rules: {
        semi: ['error', 'always'],
        // other rules...
    },
    plugins: [
        vuePlugin,
        typescriptPlugin,
    ],
};
