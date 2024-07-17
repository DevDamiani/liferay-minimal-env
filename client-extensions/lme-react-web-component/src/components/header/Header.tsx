import React from 'react';

interface Props {
  title: string
  header: string
  name: string
}

const DynamicTag:React.FC<any> = ({ tagName, ...props }) => {
  return React.createElement(tagName, props);
};
function Header({title = "test", name = "default", header = "h1"}: Props) {
  return (
    <div>
      <DynamicTag tagName={header}>Title novo amigo HEHE- {title}</DynamicTag>
      <p>By:{name}</p>
      <div className='flex'>
        <p>ola hehe</p>
      </div>
    </div>
  )
}
export default Header