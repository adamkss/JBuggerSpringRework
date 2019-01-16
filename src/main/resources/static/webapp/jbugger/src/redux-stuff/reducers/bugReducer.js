import { SET_BUGS, ADD_BUG } from '../actions/actionTypes'

const initialState = {
    allBugs: [],
    bugsByStatus: {}
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

const bugReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_BUGS:
            return {
                ...state,
                allBugs: action.data,
                bugsByStatus: mapBugsToObjectByStatus(action.data)
            }
        case ADD_BUG: {
            return {
                ...state,
                allBugs: [...state.allBugs, action.newBug],
                bugsByStatus: addBugByStatus(state.bugsByStatus, action.newBug)
            }
        }
        default:
            return state;
    }
}

export default bugReducer;