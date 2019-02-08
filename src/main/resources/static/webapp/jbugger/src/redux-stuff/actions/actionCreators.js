import {SET_BUGS, ADD_BUG, FILTER_BUGS, MOVE_BUG} from './actionTypes'

export const setBugs = (bugs) => {
    return {
        type: SET_BUGS,
        data: bugs
    }
}

export const addBug = (newBug) => {
    return {
        type: ADD_BUG,
        newBug
    }
}

export const getAllBugs =  () => {
    return (dispatch) => {
        fetch('http://localhost:8080/bugs')
            .then((response) => response.json())
            .then((allBugs) => dispatch(setBugs(allBugs)));
    }
}

export const createBug =  (newBugWithStatus) => {
    return (dispatch) => {
        fetch('http://localhost:8080/bugs', {
            method: "POST",
            body: JSON.stringify(newBugWithStatus),
            headers: {
                'Content-Type': 'application/json'
            },
        })
            .then((response) => response.json())
            .then((createdBug) => dispatch(addBug(createdBug)));
    }
}

export const filterBugs = (filterString) => {
    return {
        type: FILTER_BUGS,
        filterString
    }
}

export const moveBug = (bugId, oldStatus, newStatus) => {
    return {
        type: MOVE_BUG,
        data: {
            bugId,
            oldStatus,
            newStatus
        }
    }
}