import React, { FunctionComponent, useState } from 'react';

import Card from './Card';
import { createRoot, Root } from 'react-dom/client';


class CardWebComponent extends HTMLElement {
    constructor(){
        super()
        this.mountPoint = document.createElement('span');
        this.attachShadow({ mode: 'open' }).appendChild(this.mountPoint);
        
    }

    static get observedAttributes() {
        return ['title', 'header', 'name']
    }

    mountPoint!: HTMLSpanElement;
    root!: Root;

    render() {
        const title = this.getAttribute('title') || 'test';
        const icon = this.getAttribute('icon') || '';
        const tags = this.getAttribute('tags') || '';
        const liferayProps = this.getAttribute('liferayProps') || '';

        if (!this.root) 
            this.root = createRoot(this.mountPoint);

        this.root.render(<Card title={title} icon={icon} tags={tags} liferayProps={liferayProps}/>);
    }
    connectedCallback() {
        this.render();
    }
    attributeChangedCallback() {
        this.render();
    }
}
export default CardWebComponent;

const COMPONENT_NAME = 'mtui-card'

if (customElements.get(COMPONENT_NAME)) {
	console.log(
		'Skipping registration for <liferay-sample-custom-element-4> (already registered)'
	);
}
else {
	customElements.define(COMPONENT_NAME, CardWebComponent);
}