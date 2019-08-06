import '@vaadin/vaadin-lumo-styles/all-imports';

const $_documentContainer = document.createElement('template');
$_documentContainer.innerHTML = `

<custom-style>
  <style>
html {
      --lumo-border-radius: 0px;
      --lumo-size-xl: 3rem;
      --lumo-size-l: 2.5rem;
      --lumo-size-m: 2rem;
      --lumo-size-s: 1.75rem;
      --lumo-size-xs: 1.5rem;
      
      --text-ellipsis: {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }

  </style>
</custom-style>

<custom-style>
<style>
  vaadin-app-layout vaadin-tab a:hover {
    text-decoration: none;
  }
</style>
</custom-style>

<dom-module id="app-layout-theme" theme-for="vaadin-app-layout">
  <template>
    <style>
      [part="navbar"] {
        align-items: center;
        justify-content: center;
      }
    </style>
  </template>
</dom-module>
`;
document.head.appendChild($_documentContainer.content);
