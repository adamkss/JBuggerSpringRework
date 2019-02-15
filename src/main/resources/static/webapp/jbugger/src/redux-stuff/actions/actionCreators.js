import { SET_BUGS, ADD_BUG, FILTER_BUGS, MOVE_BUG_VISUALLY, WAITING_FOR_BUG_UPDATE, SET_STATUSES, BUG_CLICKED, CLOSE_MODAL, GET_USER_NAMES, SET_USER_NAMES, SET_BUG, UPDATE_CURRENTLY_ACTIVE_BUG } from './actionTypes'
import axios from 'axios';

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

export const setStatuses = (statuses) => {
    return {
        type: SET_STATUSES,
        data: statuses
    }
}

export const setBugWithId = (modifedBug) => {
    return {
        type: SET_BUG,
        data: modifedBug
    }
}

export const getAllStatuses = () => {
    return (dispatch) => {
        fetch('http://localhost:8080/statuses')
            .then((response) => response.json())
            .then((response) => {
                dispatch(setStatuses(response))
                dispatch(getAllBugs())
            });
    }
}

export const getAllBugs = () => {
    return (dispatch) => {
        fetch('http://localhost:8080/bugs')
            .then((response) => response.json())
            .then((allBugs) => dispatch(setBugs(allBugs)));
    }
}

export const createBug = (newBugWithStatus) => {
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

export const moveBugVisually = (bugId, oldStatus, newStatus) => {
    return {
        type: MOVE_BUG_VISUALLY,
        data: {
            bugId,
            oldStatus,
            newStatus
        }
    }
}
export const waitingForBugUpdate = () => {
    return {
        type: WAITING_FOR_BUG_UPDATE
    }
}

export const moveBug = (bugId, oldStatus, newStatus) => {
    return (dispatch) => {
        dispatch(waitingForBugUpdate());
        axios.put(`http://localhost:8080/bugs/bug/${bugId}/status`, {
            newStatus
        }).then((result) => {
            dispatch(moveBugVisually(bugId, oldStatus, newStatus));
        }).catch((error) => {
            console.log(error);
        })
    }
}

export const bugClicked = (bugId) => {
    return {
        type: BUG_CLICKED,
        data: bugId
    }
}

export const closeModal = () => {
    return {
        type: CLOSE_MODAL
    }
}

export const setUserNames = (usernames) => {
    return {
        type: SET_USER_NAMES,
        data: usernames
    }
}

export const getUserNames = () => {
    return (dispatch) => {
        fetch("http://localhost:8080/users/namesAndUsernames")
            .then((response) => response.json())
            .then((users) => dispatch(setUserNames(users)))
    }
}

export const updateCurrentlyActiveBug =(newBug) => {
    return {
        type: UPDATE_CURRENTLY_ACTIVE_BUG,
        data: newBug
    }
}

export const startUpdatingBug = (modifiedBug) => {
    return (dispatch) => {
        axios.put(`http://localhost:8080/bugs/bug/${modifiedBug.id}`, modifiedBug)
            .then((result) => {
                dispatch(setBugWithId(result.data));
                dispatch(updateCurrentlyActiveBug(result.data))
            }).catch((error) => {
                console.log(error);
            })
    }
}