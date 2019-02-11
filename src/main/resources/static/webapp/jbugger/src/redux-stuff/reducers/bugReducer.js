import { SET_BUGS, ADD_BUG, FILTER_BUGS, MOVE_BUG_VISUALLY, WAITING_FOR_BUG_UPDATE, SET_STATUSES, BUG_CLICKED, CLOSE_MODAL } from '../actions/actionTypes'

// TODO: do we really need filteredbugs to be a separate entity?
const initialState = {
    statuses: [],
    allBugs: [],
    bugsByStatus: {},
    filteredBugs: [],
    waitingForBugUpdate: false,
    filterString: null,
    activeBugToModifyID: null
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
    if (!filterString || filterString === "")
        return bugs;

    let filterStringUpperCase = filterString.toUpperCase();
    return bugs.filter(bug => bug.title.toUpperCase().includes(filterStringUpperCase));
}

const initializeBugMapFromArray = (statuses) => {
    let map = {};
    statuses.forEach(status => {
        map[status.statusName] = [];
    })
    return map;
}

const bugReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_STATUSES: {
            return {
                ...state,
                statuses: action.data,
                bugsByStatus: initializeBugMapFromArray(action.data)
            }
        }
        case SET_BUGS:
            return {
                ...state,
                allBugs: action.data,
                bugsByStatus: mapBugsToObjectByStatus(action.data),
                filteredBugs: action.data
            }
        case ADD_BUG: {
            let newAllBugs = [...state.allBugs, action.newBug];
            return {
                ...state,
                allBugs: newAllBugs,
                bugsByStatus: mapBugsToObjectByStatus(filterBugs(newAllBugs, state.filterString)),
            }
        }
        case FILTER_BUGS: {
            let filteredBugs = filterBugs(state.allBugs, action.filterString);

            return {
                ...state,
                bugsByStatus: mapBugsToObjectByStatus(filteredBugs),
                filteredBugs,
                filterString: action.filterString
            }
        }
        case WAITING_FOR_BUG_UPDATE: {
            return {
                ...state,
                waitingForBugUpdate: true
            }
        }

        case MOVE_BUG_VISUALLY: {
            if (action.data.oldStatus === action.data.newStatus)
                return {
                    ...state,
                    waitingForBugUpdate: false
                }
            let allBugsWithoutModified = [...state.allBugs.filter(bug => bug.id != action.data.bugId)];
            let modifiedBug = {
                ...state.allBugs.filter(bug => bug.id == action.data.bugId)[0],
                status: action.data.newStatus
            };
            let allBugs = [...allBugsWithoutModified, modifiedBug];

            return {
                ...state,
                allBugs: allBugs,
                bugsByStatus: mapBugsToObjectByStatus(filterBugs(allBugs, state.filterString)),
                waitingForBugUpdate: false
            }
        }
        case BUG_CLICKED: {
            return {
                ...state,
                activeBugToModifyID: action.data
            }
        }
        case CLOSE_MODAL: {
            return {
                ...state,
                activeBugToModifyID: null
            }
        }
        default:
            return state;
    }
}

export default bugReducer;