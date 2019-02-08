import { SET_BUGS, ADD_BUG, FILTER_BUGS } from '../actions/actionTypes'

// TODO: do we really need filteredbugs to be a separate entity?
const initialState = {
    allBugs: [],
    bugsByStatus: {},
    filteredBugs: []
}

const addBugByStatus = function (oldBugsByStatus, newBug) {
    let newBugByStatus = { ...oldBugsByStatus };
    newBugByStatus[newBug.status] = [...newBugByStatus[newBug.status], newBug];
    return newBugByStatus;
}

const mapBugsToObjectByStatus = function (bugs) {
    const bugsByStatus = {};
    bugs.forEach(bug => {
        if (!bugsByStatus[bug.status]) {
            bugsByStatus[bug.status] = [];
        }
        bugsByStatus[bug.status].push(bug);
    })
    return bugsByStatus;
}

const filterBugs = function (bugs, filterString) {
    if(!filterString || filterString === "")
        return bugs;
        
    let filterStringUpperCase = filterString.toUpperCase();
    return bugs.filter( bug => bug.title.toUpperCase().includes(filterStringUpperCase));
}

const bugReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_BUGS:
            return {
                ...state,
                allBugs: action.data,
                bugsByStatus: mapBugsToObjectByStatus(action.data),
                filteredBugs: action.data
            }
        case ADD_BUG: {
            return {
                ...state,
                allBugs: [...state.allBugs, action.newBug],
                bugsByStatus: addBugByStatus(state.bugsByStatus, action.newBug)
            }
        }
        case FILTER_BUGS: {
            let filteredBugs = filterBugs(state.allBugs, action.filterString);

            return {
                ...state,
                bugsByStatus: mapBugsToObjectByStatus(filteredBugs),
                filteredBugs
            }
        }
        default:
            return state;
    }
}

export default bugReducer;