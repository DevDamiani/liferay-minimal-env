import Header from './Header';
import { createRoot, Root } from 'react-dom/client';


class HeaderWebComponent extends HTMLElement {

    constructor(){
        super()
        console.log("ola");
        this.mountPoint = document.createElement('span');
        this.attachShadow({ mode: 'open' }).appendChild(this.mountPoint);
        
    }
    
    static get observedAttributes() {
        return ['title', 'header', 'name']
    }
    mountPoint!: HTMLSpanElement;
    root!: Root;
    render() {
        const title = this.getAttribute('title') || 'child';
        const header = this.getAttribute('header') || 'h2';
        const name = this.getAttribute('name') || 'default';

        if (!this.root) 
            this.root = createRoot(this.mountPoint);

        this.root.render(<Header header={header} title={title} name={name} />);
    }
    connectedCallback() {
        this.render();
    }
    attributeChangedCallback() {
        this.render();
    }
}
export default HeaderWebComponent;

const COMPONENT_NAME = 'webcomponent-title';