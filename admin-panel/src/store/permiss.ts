import {defineStore} from 'pinia';

interface ObjectList {
    [key: string]: string[];
}

export const usePermissStore = defineStore('permiss', {
    state: () => {
        const keys = localStorage.getItem('ms_keys');
        return {
            key: keys ? JSON.parse(keys) : <string[]>[],
            defaultList: <ObjectList>{
                admin: ['1','001', '002'],
                user: []
            }
        };
    },
    actions: {
        handleSet(val: string[]) {
            this.key = val;
        }
    }
});
