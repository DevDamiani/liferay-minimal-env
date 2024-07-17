import React from 'react';

import BasicWebComponent from '@components/shared/BasicWebComponent';

interface Props {
    title: String
    icon: String
    tags: String
    liferayProps: String

}
function Card({ title, icon, tags, liferayProps }: Props) {

    const prefix = "mtui"

    function hasValue(str: String | null | undefined): boolean {
        return str !== null && str !== undefined && str.trim() !== "";
    }

    return (

        <BasicWebComponent>

            <div 
                className={`${prefix}-card`} 
                style={{minHeight: '200px', display: 'flex', flexDirection: 'column', justifyContent: 'space-between'}}
                >

                <div className={`${prefix}-card__wrapper`}>

                    {hasValue(icon) &&
                        <div className={`${prefix}-card__icon mtui-card__icon--outside`}>
                            <i className={`fa ${icon}`}></i>
                        </div>
                    }

                    <div className={`${prefix}-card__title h4 line-clamp-3`}>
                        {title}
                    </div>


                </div>

                {hasValue(tags) &&
                    <div className={`${prefix}-card__bottom`}>
                        <div className="d-flex">
                            <div className={`${prefix}-badges-wrapper`}>
                                <a href="#" className={`${prefix}-badge ${prefix}-badge--primary`}>Cidadão</a>
                                <a href="#" className={`${prefix}-badge ${prefix}-badge--info`}>Servidor</a>
                            </div>
                        </div>

                    </div>
                }

            </div>

        </BasicWebComponent>

    )
}

export default Card