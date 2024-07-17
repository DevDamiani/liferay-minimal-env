import React, { FunctionComponent, useState } from 'react';
import { createRoot, Root } from 'react-dom/client';

import CardIcon from './CardIcon';


class CardIconWebComponent extends HTMLElement {
    constructor(){
        super()
        this.mountPoint = document.createElement('span');
        this.attachShadow({ mode: 'open' }).appendChild(this.mountPoint);
        
    }

    static get observedAttributes() {
        return ['icon']
    }

    mountPoint!: HTMLSpanElement;
    root!: Root;

    render() {
        const icon = this.getAttribute('icon') || 'fa-solid fa-pen-to-square';
        const title = this.getAttribute('title') || 'test';
        const liferayProps = this.getAttribute('liferayProps') || '';

        if (!this.root) 
            this.root = createRoot(this.mountPoint);

        this.root.render(<CardIcon icon={icon} title={title} liferayProps={liferayProps}/>);
    }
    connectedCallback() {
        this.render();
    }
    attributeChangedCallback() {
        this.render();
    }
}
export default CardIconWebComponent;

const COMPONENT_NAME = 'mtui-card-icon'

if (customElements.get(COMPONENT_NAME)) {
	console.log(
		'Skipping registration for <liferay-sample-custom-element-4> (already registered)'
	);
}
else {
	customElements.define(COMPONENT_NAME, CardIconWebComponent);
}